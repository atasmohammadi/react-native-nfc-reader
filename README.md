react-native-nfc-reader
============================

Reads NFC Tag ID.

Add it to your project
-------------------------

* `android/settings.gradle`

```gradle
...
include ':react-native-nfc-reader'
project(':react-native-nfc-reader').projectDir = new File(settingsDir, '../node_modules/react-native-nfc-reader/android')
```

* `android/app/build.gradle`

```gradle
...
dependencies {
    ...
    compile project(':react-native-nfc-reader')
}
```

* register module (in MainApplication.java)

  * For react-native below 0.19.0 (use `cat ./node_modules/react-native/package.json | grep version`)

```java
import com.ataomega.reactnativenfcreader; // <------ add package

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

  ......

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mReactRootView = new ReactRootView(this);

    mReactInstanceManager = ReactInstanceManager.builder()
      .setApplication(getApplication())
      .setBundleAssetName("index.android.bundle")
      .setJSMainModuleName("index.android")
      .addPackage(new MainReactPackage())
      .addPackage(new ReactNativeNFCReader())      // <------- add package
      .setUseDeveloperSupport(BuildConfig.DEBUG)
      .setInitialLifecycleState(LifecycleState.RESUMED)
      .build();

    mReactRootView.startReactApplication(mReactInstanceManager, "ExampleRN", null);

    setContentView(mReactRootView);
  }

  ......

}
```

  * For react-native 0.19.0 and higher
```java
import com.ataomega.reactnativenfcreader; // <------ add package

public class MainApplication extends Application implements ReactApplication {
   // ...
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
        new MainReactPackage(), // <---- add comma
        new ReactNativeNFCReader() // <---------- add package
      );
    }
```

Api
----

### Setup
```js
import React, {
  DeviceEventEmitter // will emit events that you can listen to
} from 'react-native';

import { ReactNativeNFCReader } from 'NativeModules';
```


### NFC Reader
```js
DeviceEventEmitter.addListener('ReactNativeNFCReader', function (data) {
  /**
  * NFC Card Tag ID.
  **/
});
```
