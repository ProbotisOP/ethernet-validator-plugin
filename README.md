# Ethernet Validator Cordova Plugin

## 🌟 Overview

Tired of proxies causing your Ethernet connection to drop? When the Ethernet cable is plugged in or out, captive portal authentication often fails due to IP rules, whitelisting, or proxy configurations. This plugin detects if the captive URL authentication for Ethernet is successful and ensures the network is available for use, so your app doesn’t get blamed when the issue lies within the Ethernet network.


## 🚀 Getting Started

### Installation

```bash
cordova plugin add https://github.com/ProbotisOP/ethernet-validator-plugin.git
```

### Basic Usage

```javascript
cordova.plugins.EthernetValidator.validateEthernet(
  function(message) {
    console.log("Ethernet is valid: " + message);
  },
  function(error) {
    console.error("Validation failed: " + error);
  }
);
```

## 📘 API Reference

- `validateEthernet(successCallback, errorCallback)`: Performs a comprehensive Ethernet validation.
- `getEthernetMetrics(successCallback, errorCallback)`: Retrieves detailed metrics about the current Ethernet connection.

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for more details.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋‍♀️ Support

Having troubles? Check out our [FAQ](FAQ.md) or [open an issue](https://github.com/ProbotisOP/ethernet-validator-plugin/issues/new).

---

Made with ❤️ by [ProbotisOP](https://github.com/ProbotisOP)
