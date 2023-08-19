package io.aelf.portkey.init;

import io.aelf.portkey.storage.IStorageBehaviour;
import io.aelf.portkey.utils.log.ILogger;

public class SDkInitConfig {
    private final ILogger logger;
    private final IStorageBehaviour storageHandler;

    protected SDkInitConfig(Builder builder) {
        this.logger = builder.logger;
        this.storageHandler = builder.storageHandler;
    }

    public ILogger getLogger() {
        return logger;
    }

    public IStorageBehaviour getStorageHandler() {
        return storageHandler;
    }

    public class Builder {
        private ILogger logger;
        private IStorageBehaviour storageHandler;

        public Builder setLogger(ILogger logger) {
            this.logger = logger;
            return this;
        }

        public Builder setStorageHandler(IStorageBehaviour storageHandler) {
            this.storageHandler = storageHandler;
            return this;
        }

        public SDkInitConfig build() {
            return new SDkInitConfig(this);
        }
    }
}
