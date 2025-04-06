ALTER TABLE `estud-io`.users ADD token_email_verification TEXT NULL;
ALTER TABLE `estud-io`.users ADD email_verified BIT NOT NULL DEFAULT 0;