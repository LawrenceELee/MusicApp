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

The music player app should be both a "Started" and "Bound" Service. Otherwise, if it were just a BoundService then the process will stop when the last client disconnects (when the back button is pressed).
If it is hybrid of both, then when we press back the music will continue playing. And when we open/start the Activity again it will bind to the Service which is still running.

A Process can be thought of as a group of threads. By default each app runs in it's own process. The main UI thread is part of this process as well as any new threads that the process creates. Some apps run on multiple processes. For example, a music player might run on 2 processes: one for the activity and one for the process. This way it doubles the access to memory (since each process is restricted to the amount of memory they can use). Furthermore, you might want to listen to the music in the background and the Activity might no longer be visible in the foreground. Android can then re-allocate those resources of the current foreground process. 

How does Android pick which processes to kill?
Order in highest priority (less likely to be killed) to lowest
Foreground processes - processes that the user is current interacting with.
Visibile processes - doesn't have any foreground components but can still affect what user sees (example: a paused activity)
Services processes - running started service
Background processes - if it isn't visible it is most likely a background process
Empty processes - a process in which all the components have been destroyed, but kept around for caching purposes.

By default apps have 1 proccess and 1 main thread. Don't use multiple processes unless you really have to because it can use up valuable resources.

Messenger is a reference to a Handler. A Messenger can also transformed into a Binder and created from a Binder.