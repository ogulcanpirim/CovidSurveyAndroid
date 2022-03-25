const wd = require("wd");
const config = require("../config");

const sampleTest = async () => {
  const driver = wd.promiseChainRemote(config.options.server);

  await driver.init(config.options.capabilities);
  await driver.setImplicitWaitTimeout(5000);
  await driver.elementById("com.example.covidsurvey:id/textInputEditNameSurname").click();
};

exports.sampleTest = sampleTest;

