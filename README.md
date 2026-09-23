# Planit Technical Assessment - Automation

UI automation for the Jupiter Toys demo site (`http://jupiter.cloud.planittesting.com`),
written as Cucumber/Gherkin scenarios in Java, using [Playwright](https://playwright.dev/java/)
for browser automation, [Cucumber-JVM](https://cucumber.io/docs/installation/java/) to run
the `.feature` files, and JUnit 5's Platform Suite engine to execute them - a plain
Maven project.

## What's covered

| Test | Feature file | Scenario |
|---|---|---|
| 1 | `ContactFormValidation.feature` | Submitting the empty contact form shows validation errors; populating the mandatory fields (Forename, Email, Message) clears them. |
| 2 | `ContactFormSubmission.feature` | A fully populated contact form submits successfully. Written as a Scenario Outline with 5 Examples rows - the standard Cucumber idiom for "run this N times" - so the pipeline reports 5 independent pass/fail results. |
| 3 | `ShoppingCart.feature` | Buys 2x Stuffed Frog, 5x Fluffy Bunny, 3x Valentine Bear, then on the cart page verifies each product's price and subtotal, and that the cart total equals the sum of every subtotal. |

Test cases 1 and 2 are kept in separate feature files, one per test case as the
assessment brief lists them, even though they share the same step definitions
(`ContactFormSteps.java`).

## Project structure

```
src/test/
├── resources/features/             # Gherkin scenarios - the executable spec
│   ├── ContactFormValidation.feature   # Test case 1
│   ├── ContactFormSubmission.feature   # Test case 2
│   └── ShoppingCart.feature             # Test case 3
└── java/com/planitautomation/
    ├── runners/RunCucumberTest.java # JUnit 5 Platform Suite entry point Maven runs
    ├── stepdefinitions/             # Glue code: Gherkin steps -> page object calls
    │   ├── NavigationSteps.java
    │   ├── ContactFormSteps.java
    │   └── ShoppingCartSteps.java
    ├── pages/                       # Page Object Model - one class per app page
    │   ├── BasePage.java
    │   ├── HomePage.java
    │   ├── ContactPage.java
    │   ├── ShopPage.java
    │   └── CartPage.java
    ├── support/
    │   ├── TestContext.java         # Shares page objects between step classes in a scenario
    │   └── Hooks.java               # @Before/@After: browser lifecycle
    └── utils/CurrencyParser.java    # Turns "$10.99" / "Total: 116.9" text into BigDecimal
```

Gherkin scenarios describe *what* the site should do in plain English; step definitions
translate each line into calls on a page object; page objects are the only place that
knows about CSS selectors and Playwright locators.

Cucumber-JVM (via `cucumber-picocontainer`) creates a new `TestContext` per scenario and
injects it into `Hooks` and every step-definition class via their constructors - that's
how, for example, `ContactFormSteps` gets hold of the same `ContactPage` that
`NavigationSteps` navigated to a moment earlier in the same scenario. `Hooks` starts a
fresh headless Chromium context + page in `@Before` and tears it down in `@After`, so
scenarios never leak cookies, storage or navigation state into one another.

## Prerequisites

- JDK 21+
- Maven 3.9+
- Playwright's browser binaries (installed once, see below)

## Setup

```bash
mvn clean compile test-compile

# Install the browser Playwright drives (one-time, or whenever the Playwright
# dependency is upgraded)
mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" \
    -Dexec.classpathScope=test -Dexec.args="install --with-deps chromium"
```

## Running the tests

Cucumber-JVM generates a JUnit 5 dynamic test per Gherkin scenario (and per Examples row
of a Scenario Outline) under the `RunCucumberTest` suite, so the usual `mvn test`
workflow applies - no separate Cucumber CLI needed.

```bash
mvn test
```

Results land under `target/cucumber-reports/cucumber.xml` (JUnit XML, for CI) and
`target/surefire-reports/`.

## Continuous integration

A `Jenkinsfile` at the repo root defines a declarative pipeline: build -> install the
Playwright browser -> `mvn test`, publishing `cucumber.xml` so Jenkins' Test Result Trend
can chart pass/fail history. The same stages translate directly to any other CI runner.

## Design notes

- **Cucumber-JVM on the JUnit 5 Platform, not JUnit 4**: `RunCucumberTest` uses
  `@Suite`/`@IncludeEngines("cucumber")` from `cucumber-junit-platform-engine` - no
  `cucumber-junit` (JUnit 4 runner) dependency needed.
- **"Run 5 times" as a Scenario Outline**: Cucumber has no native "repeat this
  scenario" concept, so the idiomatic way to express it is a Scenario Outline with 5
  Examples rows - each becomes its own fully independent, individually reported test
  (fresh browser, fresh scenario context).
- **Mandatory vs. optional fields**: the contact form only requires Forename, Email and
  Message; `fillMandatoryFields` fills exactly those three, matching the assessment's
  "populate mandatory fields" step.
- **Prices read at run time**: `ShoppingCartSteps` records each product's price from the
  shop page rather than hard-coding it, then cross-checks that value against the cart
  page and verifies `subtotal = price x quantity` and `total = sum(subtotals)`.
- **BigDecimal, not double, for money**: prices are compared with `BigDecimal.compareTo`
  rather than `equals` (scale-insensitive) to avoid floating-point rounding surprises.
- **Currency parsing**: the site renders money inconsistently ("$10.99", "21.98",
  "Total: 116.9"), so `CurrencyParser` strips everything except digits/`.`/`-` and
  parses the rest.

## Known limitation of this submission

Maven dependency resolution (`mvn compile`/`mvn test`) could not be executed in the
sandbox this project was authored in, because that environment's network policy blocks
Maven Central entirely. The code was therefore written and reviewed by hand against the
documented `Playwright`/`Cucumber-JVM`/`JUnit 5` APIs, and checked for plain Java syntax
errors with `javac` against the JDK's own standard library, rather than compiled
end-to-end in place. Everything above should build and run unmodified on a machine or CI
agent with normal internet access; if anything doesn't, it's most likely a dependency
version pin in `pom.xml` worth bumping.
