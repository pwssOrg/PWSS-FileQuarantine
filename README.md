# PWSS-FileQuarantine

## Installation
To use the `file-quarantine` library, you need to include it as a dependency in your project.
### Maven 
```xml
<dependency>
  <groupId>lib.pwss</groupId>
  <artifactId>file-quarantine</artifactId>
  <version>1.0.7</version>
</dependency>
```
## Usage

Here's a basic example to get you started with using the `file-quarantine` library for quarantining and unquarantining files:


```java
package org.pwss;

import org.pwss.quarantineManager_aes.FileQuarantineManager;
import org.pwss.quarantineManager_aes.dto.MetaDataResult;


import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        final Path path = Path.of("C:\\your\\path\\your_file.txt");
       
        FileQuarantineManager fileQuarantineManager = new FileQuarantineManager();
        MetaDataResult metaDataResult = fileQuarantineManager.quarantine(path);
        
        System.out.println(metaDataResult.keyName());
        
        fileQuarantineManager.unquarantine(metaDataResult.keyName().split(".enc")[0]);
    }
}

```

![File Integrity Scanner Image](https://github.com/pwssOrg/PWSS-FileQuarantine/blob/master/.github/assets/images/1920x1078.jpg?raw=true)

### API Documentation

For detailed information about our classes, methods, and their usage, please visit the Javadoc:

[Link to API Docs](https://pwssorg.github.io/PWSS-FileQuarantine-JavaDocs/)

The Javadocs provide comprehensive documentation for all public APIs in this project, including:
- Class descriptions
- Method details with parameters and return types
- Example usages when available


## Discussion Forum

Please visit our discussion forum for project-related documentation and discussions: [Project Discussion
Forum](https://github.com/orgs/pwssOrg/discussions/categories/pwss-filequarantine)

[![Makefile CI](https://github.com/pwssOrg/PWSS-FileQuarantine/actions/workflows/build.yml/badge.svg)](https://github.com/pwssOrg/PWSS-FileQuarantine/actions/workflows/build.yml)
[![SCA Scan - FileQuarantine](https://github.com/pwssOrg/PWSS-FileQuarantine/actions/workflows/snyk-scan.yml/badge.svg)](https://github.com/pwssOrg/PWSS-FileQuarantine/actions/workflows/snyk-scan.yml)


---







