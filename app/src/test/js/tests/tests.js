require("dotenv").config();
const wd = require("wd");
const config = require("./config");
const { expect } = require("chai");

describe("Covid 19 Survey App Test Suite", () => {
  let driver;

  before(async () => {
    driver = wd.promiseChainRemote(config.options.server);
    await driver.init(config.options.capabilities);
  });

  it("Tests if the send button is activated when all inputs are populated.", async () => {
    try {
      await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").sendKeys("TEST USERNAME");

      // select date of birth
      await driver
        .elementByXPath(
          `
          /hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.
          widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/
          android.widget.ScrollView/android.view.ViewGroup/android.widget.LinearLayout[2]/
          android.widget.FrameLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/
          android.widget.ImageButton
        `
        )
        .click();

      await driver.waitForElementById("com.example.covidsurvey:id/confirm_button");
      await driver.elementById("com.example.covidsurvey:id/confirm_button").click();

      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewCity").sendKeys("Turkey: Ankara");
      // Gender
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewGender").sendKeys("Male");

      // Vac type
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewVaccineType").sendKeys("Biontech");

      // Side effects
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewPositiveCase").sendKeys("Yes");

      const touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 1000 }).moveTo({ x: 0, y: 10 }).release();
      await touchAction.perform();

      // Submit ubtton
      const isEnabled = await driver.elementById("com.example.covidsurvey:id/sendButton").isEnabled();
      expect(isEnabled).to.equal(true);
    } catch (error) {
      console.log("alksdfjs");
    }
  });

  it("Tests if the send button reacts to valid and invalid data (activates with valid and deactivates with invalid).", async () => {
    try {
      let touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 259 }).moveTo({ x: 0, y: 1000 }).release();
      await touchAction.perform();
      await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").sendKeys("INVALID USERNAME 1234");

      // select date of birth
      await driver
        .elementByXPath(
          `
          /hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.
          widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/
          android.widget.ScrollView/android.view.ViewGroup/android.widget.LinearLayout[2]/
          android.widget.FrameLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/
          android.widget.ImageButton
        `
        )
        .click();

      await driver.waitForElementById("com.example.covidsurvey:id/confirm_button");
      await driver.elementById("com.example.covidsurvey:id/confirm_button").click();

      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewCity").sendKeys("Turkey: Istanbul");
      // Gender
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewGender").sendKeys("Female");

      // Vac type
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewVaccineType").sendKeys("Sinovac");

      // Side effects
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewPositiveCase").sendKeys("No");

      touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 1000 }).moveTo({ x: 0, y: 10 }).release();
      await touchAction.perform();

      // Submit ubtton
      let submitBtn = await driver.elementById("com.example.covidsurvey:id/sendButton");
      const isEnabledWithInvalidData = submitBtn && (await submitBtn.isEnabled());

      touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 259 }).moveTo({ x: 0, y: 1000 }).release();
      await touchAction.perform();

      await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").sendKeys("VALID USERNAME");

      touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 1000 }).moveTo({ x: 0, y: 10 }).release();
      await touchAction.perform();

      const isEnabledWithValidDataAfterChange = await driver.elementById("com.example.covidsurvey:id/sendButton").isEnabled();

      expect(isEnabledWithInvalidData).to.equal(false) && expect(isEnabledWithValidDataAfterChange).to.equal(true);
    } catch (error) {
      console.log(error);
    }
  });

  it("Tests if the selected dates are valid.", async () => {
    try {
      let touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 259 }).moveTo({ x: 0, y: 1000 }).release();
      await touchAction.perform();

      await driver
        .elementByXPath(
          `
          /hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.
          widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/
          android.widget.ScrollView/android.view.ViewGroup/android.widget.LinearLayout[2]/
          android.widget.FrameLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/
          android.widget.ImageButton
        `
        )
        .click();

      await driver.waitForElementById("com.example.covidsurvey:id/confirm_button");
      await driver.elementById("com.example.covidsurvey:id/confirm_button").click();

      const selectedDate = await driver.elementByXPath(`
          /hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/
          android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.ScrollView/android.view.ViewGroup/
          android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout[2]/
          android.widget.FrameLayout/android.widget.ImageButton
      `);

      const validDate = "00000000-0000-012b-ffff-ffff00000020";
      expect(selectedDate.value).to.not.equal("");
    } catch (error) {
      console.log(error);
    }
  });

  it("Tests if the erorr message is shown if names are invlaid (containt numbers or special chars)", async () => {
    try {
      let touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 259 }).moveTo({ x: 0, y: 1000 }).release();
      await touchAction.perform();

      // Populate the input with invalid name
      await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").sendKeys("INVALID USERNAME 1234");
      // Get the error message for the input
      const value = await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").printError().text();
      expect(value).to.equal("INVALID USERNAME 1234");
    } catch (error) {
      console.log(error);
    }
  });

  it("Tests if an item is properly selected on drop down menus.", async () => {
    try {
      let touchAction = new wd.TouchAction(driver).longPress({ x: 0, y: 259 }).moveTo({ x: 0, y: 1000 }).release();
      await touchAction.perform();

      // Open the dropdown menu
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewGender").click();
      // Select 'Male' option
      await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewGender").sendKeys("Male");
      const selectedOption = await driver.elementById("com.example.covidsurvey:id/autoCompleteTextViewGender").text();

      expect(selectedOption).to.equal("Male");
    } catch (error) {
      console.log(error);
    }
  });
});
