package io.docline.sdk.videoconsultation.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import docline.doclinevideosdk.DoclineVideocallView
import docline.doclinevideosdk.core.listeners.ArchiveListener
import docline.doclinevideosdk.core.listeners.ChatListener
import docline.doclinevideosdk.core.listeners.ConnectionListener
import docline.doclinevideosdk.core.listeners.DoclineListener
import docline.doclinevideosdk.core.listeners.enums.CameraSource
import docline.doclinevideosdk.core.listeners.enums.ResponseError
import docline.doclinevideosdk.core.listeners.enums.ScreenView
import docline.doclinevideosdk.core.listeners.enums.UserType

class MainActivity : AppCompatActivity() {

    private lateinit var doclineVideoCall: DoclineVideocallView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find component and init
        doclineVideoCall = findViewById(R.id.doclineView)
        doclineVideoCall.init(this)

        // setup listeners
        setupVideoConsultationListeners()

        // join to video consultation
        joinVideoConsultation(
            server = your_server_url,
            code = your_room_code,
            showSetupScreen = true
        )

    }

    private fun joinVideoConsultation(server: String, code: String, showSetupScreen: Boolean) {
        // prepare configuration (roomCode and serverURL)
        val configuration = hashMapOf<String, Any>(
            "serverURL" to server,  // server URL to join to the video consultation
            "roomCode" to code,     // code to join to the video consultation
            "enableSetupScreen" to showSetupScreen // shows a previous video consultation screen
        )

        // join to the video with the previous configuration defined
        doclineVideoCall.join(configuration)
    }

    private fun setupVideoConsultationListeners() {
        doclineVideoCall.generalListener = object: DoclineListener {

            // invoked when user exit from video consultation
            override fun consultationExit(userType: UserType) {
                Log.i("SDK-Video", "consultationExit(userType: ${userType.name})")
            }

            // invoked when error trying connecting to consultation
            override fun consultationJoinError(error: ResponseError) {
                Log.i("SDK-Video", "consultationJoinError(error: ${error.name})")
            }

            // invoked when user has joined successfully to video consultation
            override fun consultationJoinSuccess() {
                Log.i("SDK-Video", "consultationJoinSuccess()")
            }

            // invoked when consultation joined successfully
            override fun consultationJoined() {
                Log.i("SDK-Video", "consultationJoined()")
            }

            // invoked when consultation
            override fun consultationRejoin(userType: UserType) {
                Log.i("SDK-Video", "consultationRejoin(userType: ${userType.name})")
            }

            // invoked when consultation finished successfully
            override fun consultationTerminated(screenView: ScreenView) {
                Log.i("SDK-Video", "consultationTerminated(screenView: ${screenView.name})")
            }

            // invoked when shows a new screen
            override fun show(screenView: ScreenView) {
                Log.i("SDK-Video", "show(screenView: ${screenView.name})")
            }

            // invoked when user switch front / back camera
            override fun updatedCamera(screenView: ScreenView, source: CameraSource) {
                Log.i("SDK-Video", "updatedCamera(screenView: ${screenView.name}, source: ${source.name})")
            }

            // invoked when user enabled or disabled camera
            override fun updatedCamera(screenView: ScreenView, isEnabled: Boolean) {
                Log.i("SDK-Video", "updatedCamera(screenView: ${screenView.name}, isEnabled: $isEnabled)")
            }

            // invoked when user enabled or disabled microphone
            override fun updatedMicrophone(screenView: ScreenView, isEnabled: Boolean) {
                Log.i("SDK-Video", "updatedMicrophone(screenView: ${screenView.name}, isEnabled: $isEnabled)")
            }
        }

        doclineVideoCall.connectionListener = object: ConnectionListener {

            // invoked when user reconnect to the video consultation
            override fun consultationReconnected() {
                Log.i("SDK-Video", "consultationReconnected()")
            }

            // invoked when user is reconnecting to the video consultation
            override fun consultationReconnecting() {
                Log.i("SDK-Video", "consultationReconnecting()")
            }

            // invoked when video consultation has disconnected by error
            override fun disconnectedByError() {
                Log.i("SDK-Video", "disconnectedByError()")
            }

            // invoked when user has pressed exit button
            override fun userSelectExit() {
                Log.i("SDK-Video", "userSelectExit()")
            }

            // invoked when user is trying to reconnect
            override fun userTryReconnect() {
                Log.i("SDK-Video", "userTryReconnect()")
            }
        }

        doclineVideoCall.archiveListener = object: ArchiveListener {

            // invoked when has been approved the screen recording
            override fun screenRecordingApproved() {
                Log.i("SDK-Video", "screenRecordingApproved()")
            }

            // invoked when has been denied the screen recording
            override fun screenRecordingDenied() {
                Log.i("SDK-Video", "screenRecordingDenied()")
            }

            // invoked when the screen recording has been finished
            override fun screenRecordingFinished() {
                Log.i("SDK-Video", "screenRecordingFinished()")
            }

            // invoked when the screen recording has started
            override fun screenRecordingStarted() {
                Log.i("SDK-Video", "screenRecordingStarted()")
            }
        }

        doclineVideoCall.chatListener = object: ChatListener {

            // invoked when user has received a chat message
            override fun messageReceived() {
                Log.i("SDK-Video", "messageReceived()")
            }

            // invoked when user has sent a chat message
            override fun messageSent() {
                Log.i("SDK-Video", "messageSent()")
            }
        }
    }

}