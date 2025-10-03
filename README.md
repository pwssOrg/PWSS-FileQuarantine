# PWSS-FileQuarantine


## Installation

To use the `file-quarantine` library, you need to include it as a dependency in your project. You can find
it on the private GitHub package repository of PWSS. A GitHub packages token is required to access it. Contact
PWSS representatives if you're interested.

### Maven 
```xml
<dependency>
  <groupId>lib.pwss</groupId>
  <artifactId>file-quarantine</artifactId>
  <version>1.0</version>
</dependency>
```
<sub>(PWSS Private Github Package)</sub>
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

## Discussion Forum

Please visit our discussion forum for project-related documentation and discussions: [Project Discussion
Forum](https://github.com/orgs/pwssOrg/discussions/categories/pwss-filequarantine)

---

© 2025 pwssOrg. All rights reserved.

