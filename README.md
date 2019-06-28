
# rn-android-webview

## Getting started

`$ npm install rn-android-webview --save`

### Mostly automatic installation

`$ react-native link rn-android-webview`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.chyrain.rn.webview.RNAAndroidWebviewPackage;` to the imports at the top of the file
  - Add `new RNAAndroidWebviewPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':rn-android-webview'
  	project(':rn-android-webview').projectDir = new File(rootProject.projectDir, 	'../node_modules/rn-android-webview/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':rn-android-webview')
  	```


## Usage
```javascript
import RNAAndroidWebview from 'rn-android-webview';

// TODO: What to do with the module?
RNAAndroidWebview;
```
  