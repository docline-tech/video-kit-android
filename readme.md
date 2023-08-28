# SDK Video consultation Example

## Requisites
    * Android SDK min version: 23 - Android 6.0 (Marshmallow).
    * Java Virtual Machine 17.
    * Docline API videocalls service available.


## Configuration:
You need add the following lines to `settings.gradle` (root project) in order to find libraries in maven repositories:
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://nexus.docline.com/repository/maven-public/' }
        maven { url 'https://tokbox.bintray.com/maven' }
        maven { url 'https://jcenter.bintray.com' }
    }
}
```
Also, you need to add the following code to your `build.gradle` (app root folder):
```
plugins {
    id 'kotlin-kapt'
}
android {
    kotlinOptions {  
	    jvmTarget = '17'  
	}
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    buildFeatures {  
	  dataBinding = true  
	  viewBinding = true  
	}
}
dependencies {
    implementation 'io.docline:video-consultation:1.1.0'
}
```

## Simple usage:
Add our component to your layout.xml:
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:orientation="vertical">
    
    <docline.doclinevideosdk.DoclineVideocallView
        android:id="@+id/doclineView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

Then use it in your Activity or Fragment:
```
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // find component and init
    val doclineVideoCall = findViewById<DoclineVideocallView>(R.id.doclineView)
    doclineVideoCall.init(this)
    
    // prepare configuration (roomCode and serverURL)
    val configuration = hashMapOf<String, Any>(
        "serverURL" to "url",   // server URL to join to the video consultation
        "roomCode" to "ea"      // code to join to the video consultation
    )

    // join to the video with the previous configuration defined
    doclineVideoCall.join(configuration)
}
```

## Extended usage:
Optionally, you can add some listeners:

`DoclineListener:`
```
doclineVideoCall.generalListener = object: DoclineListener {

    // invoked when user exit from video consultation
    override fun consultationExit(userType: UserType) {}

    // invoked when error trying connecting to consultation
    override fun consultationJoinError(error: ResponseError) {}

    // invoked when user has joined successfully to video consultation
    override fun consultationJoinSuccess() {}

    // invoked when consultation joined successfully
    override fun consultationJoined() {}

    // invoked when consultation
    override fun consultationRejoin(userType: UserType) {}

    // invoked when consultation finished successfully
    override fun consultationTerminated(screenView: ScreenView) {}

    // invoked when shows a new screen
    override fun show(screenView: ScreenView) {}

    // invoked when user switch front / back camera
    override fun updatedCamera(screenView: ScreenView, source: CameraSource) {}

    // invoked when user enabled or disabled camera
    override fun updatedCamera(screenView: ScreenView, isEnabled: Boolean) {}

    // invoked when user enabled or disabled microphone
    override fun updatedMicrophone(screenView: ScreenView, isEnabled: Boolean) {}
}
```
`ConnectionListener:`
```
doclineVideoCall.connectionListener = object: ConnectionListener {

    // invoked when user reconnect to the video consultation
    override fun consultationReconnected() {}

    // invoked when user is reconnecting to the video consultation
    override fun consultationReconnecting() {}

    // invoked when video consultation has disconnected by error
    override fun disconnectedByError() {}

    // invoked when user has pressed exit button
    override fun userSelectExit() {}

    // invoked when user is trying to reconnect
    override fun userTryReconnect() {}
}

```
`ArchiveListener:`
```
doclineVideoCall.archiveListener = object: ArchiveListener {

    // invoked when has been approved the screen recording
    override fun screenRecordingApproved() {}

    // invoked when has been denied the screen recording
    override fun screenRecordingDenied() {}

    // invoked when the screen recording has been finished
    override fun screenRecordingFinished() {}

    // invoked when the screen recording has started
    override fun screenRecordingStarted() {}
}

```
`ChatListener:`
```
doclineVideoCall.chatListener = object: ChatListener {

    // invoked when user has received a chat message
    override fun messageReceived() {}

    // invoked when user has sent a chat message
    override fun messageSent() {}
}
```
