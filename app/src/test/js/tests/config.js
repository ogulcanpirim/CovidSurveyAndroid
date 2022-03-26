exports.options = {
  capabilities: {
    displayName: "",
    platformName: "Android",
    udid: "emulator-5554",
    platformVersion: "",
    app: process.env.APK_PATH
  },

  server: {
    host: "localhost",
    port: 4723,
  },
};
