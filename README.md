Music App to learn about Android threads and services.

Runnable is a type that is fed into Thread constructors to make a new thread object.

Thread is a light weight process. They are usually spawned from the main ui thread to do another task in the background. This way it doesn't bog down the main ui thread and it remains responsive.

Handler is an interface for creating and posting messages. 

Looper manages the thread's message queue. They continuously loop around the message queues looking for new messages/tasks.

Messages and MessageQueues:
message queues are a common space to share information between threads. When one thread is done with a task it leaves a message in the queue to the main ui thread that it is done. When the main ui thread has free time, it goes to the queue to see which of the spawned threads is done with their tasks.

Services allow the app to continue running even if the main process is killed. If the main process is kill, the child threads also die. This is important since the Android OS routinely kills process to free up resources. It is an application component that performs long run operations in the background and does not provide the user interface.

Services are not threads, they sort of wrap around threads so that even when the main process dies, it will restart in the background. Normal threads die when the main process dies.

The return value of onStartCommand() represents the SERVICE'S PROCESS if it is kill after it starts (returns from onStartCommand) but before it is stopped (before stopSelf() is called) (i.e. return value is what is returned when process is killed without finishing its work).
There are 4 return values, but only 3 are important:
START_STICKY - tells android to restart service using a null intent. this is useful for activities that you want to leave running for a long time. for example, a music player or a jogging app. once the app is started, it is up to the user to decide when to stop it. if i start the jogging app and android kills it, i would be very unhappy if it doesn't restart and track the rest of my run.
START_NOT_STICKY - service won't be restarted. for example, an app that check your email inbox. it checks my inbox every 5 mins for any new emails. but if android is so burden (low on resources) that it kills your app, it isn't a big deal since the app is scheduled to restart again in 5 mins anyway.
START_REDELIVER_INTENT - our service's onStartCommand will be restarted using the last delivered intent. for example, a song downloader app. if the app gets killed while it is only downloaded 2 of 5 songs, we want it to continue at the 3rd song, not restart at 1st song.

Since Services is so common Android wrote a class IntentService for us to write Services easier.
IntentService is for handling Intents one at a time on a separate thread.
No longer need DownloadService, DownloadHandler, DownloadThread because DownloadIntentService handles all of that. But they are good for referencing.

BoundService is the second way to work with a service (the first is a started service IntentService). A BoundService is analogous to a server and the Activity binding to it is the client. Example, an app for music playback. Once the music player is started (Service), you can tell it to pause, stop, rewind, skip, etc.

Lifecycle of a Service (simpler than lifecycle of Activity):
Started Service:
1) Call to startService()
2) onCreate() called
3) onStartedCommand() called
4) Service is running
5) onDestroy() called
6) Service is shutdown

Bound Service:
1) call to bindService()
2) onCreate() called
3) onBind() called
4) Clients are bound to a service
5) onUnbind() called
6) onDestroy() called
7) Service is shutdown.

You can also have a Service that is "Started" and "Bound", but lifecycle is a little more complicated.

