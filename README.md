# PWSS-FileQuarantine


## Example

```
import org.pwss.quarantineManager_aes.FileQuarantineManager;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {

        final Path path = Path.of("C:\\tobias-fischer-PkbZahEG2Ng-unsplash.jpg");
        FileQuarantineManager fileQuarantineManager = new FileQuarantineManager();

        fileQuarantineManager.quarantine(path);
        fileQuarantineManager.unquarantine("tobias-fischer-PkbZahEG2Ng-unsplash.jpg");
    }
}

```

## Discussion Forum

Please visit our discussion forum for project-related documentation and discussions: [Project Discussion
Forum](https://github.com/orgs/pwssOrg/discussions/categories/pwss-filequarantine)

---

© 2025 pwssOrg. All rights reserved.
