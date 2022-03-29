require("dotenv").config();
const wd = require("wd");
const config = require("./config");
const { expect } = require("chai");

describe("Covid 19 Survey App Test Suite", () => {
  let driver;

  beforeEach(async () => {
    driver = wd.promiseChainRemote(config.options.server);
    await driver.init(config.options.capabilities);
  });

  afterEach(async () => {
    await driver.quit();
  });

  it("TEST CASE 1", async () => {
    await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").click();
    expect(1).to.equal(1);
  });
});