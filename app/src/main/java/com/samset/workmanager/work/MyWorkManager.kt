package com.samset.workmanager.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.samset.workmanager.R


/*
  Copyright (C) WorkManager-jetpack - All Rights Reserved Ⓒ
  Unauthorized copying of this file, via any medium is strictly prohibited
  Proprietary and confidential.
  See the License for the specific language governing permissions and limitations under the License.
 
  Created by Sanjay Singh on 10,December,2022 at 2:43 AM for WorkManager-jetpack.
  New Delhi,India
 */


class MyWorkManager(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        createNotification("Background Task", "This notification is generated by workManager")
        return Result.success()
    }

    private fun createNotification(title: String, description: String) {

        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "101")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()

        notificationManager.notify(2, notificationBuilder)

    }
}


/*
class MyDeviceAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        // Code to be executed when the admin is enabled
    }

    override fun onDisabled(context: Context, intent: Intent) {
        // Code to be executed when the admin is disabled
    }

    override fun onPasswordChanged(context: Context, intent: Intent) {
        // Code to be executed when the password is changed
    }
}
*/
