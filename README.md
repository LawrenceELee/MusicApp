Music App to learn about Android threads and services.

Runnable is a type that is fed into Thread constructors to make a new thread object.

Thread is a light weight process. They are usually spawned from the main ui thread to do another task in the background. This way it doesn't bog down the main ui thread and it remains responsive.

Handler is an interface for ccreating and posting messages. 

Looper manages the thread's message queue. They continously loop around the message queues looking for new messages/tasks.

Messages and MessageQueues:
messsage queues are a common space to share information between threads. When one thread is done with a task it leaves a message in the queue to the main ui thread that it is done. When the main ui thread has free time, it goes to the queue to see which of the spawned threads is done with their tasks.

Services allow the app to continue running even if the main process is killed. If the main process is kill, the child threads also die. This is important since the Android OS routinely kills process to free up resources. It is an application component that performs long run operations in the background and does not provide the user interface.
