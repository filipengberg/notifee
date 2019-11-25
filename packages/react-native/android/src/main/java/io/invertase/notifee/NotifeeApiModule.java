package io.invertase.notifee;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.tasks.Tasks;

import java.util.Objects;

import io.invertase.notifee.core.NotifeeNativeModule;

public class NotifeeApiModule extends NotifeeNativeModule {
  private static final String TAG = "NotifeeApiModule";

  NotifeeApiModule(ReactApplicationContext reactContext) {
    super(reactContext, TAG);
  }

  @ReactMethod
  public void displayNotification(ReadableMap notificationRaw, Promise promise) {
    Tasks.call(getExecutor(), () -> {
      NotifeeNotification notifeeNotification = NotifeeNotification.fromReadableMap(
        notificationRaw);
      notifeeNotification.displayNotification();
      return notifeeNotification;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(Objects.requireNonNull(task.getResult()).toWritableMap());
      } else {
        Exception exception = task.getException();
        Log.e(TAG, "Error displaying a notification", exception);
        if (exception instanceof InvalidNotificationParameterException) {
          InvalidNotificationParameterException notificationParameterException = (InvalidNotificationParameterException) exception;
          rejectPromiseWithCodeAndMessage(
            promise,
            notificationParameterException.getCode(),
            notificationParameterException.getMessage(),
            notificationParameterException
          );
        } else {
          rejectPromiseWithExceptionMap(promise, exception);
        }
      }
    });
  }

  // TODO handle cancel notifications

  @ReactMethod
  public void cancelNotification(String notificationId, Promise promise) {
    promise.resolve(null);
  }

  @ReactMethod
  public void cancelAllNotifications(Promise promise) {
    promise.resolve(null);
  }

  @ReactMethod
  public void createChannel(ReadableMap channelMap, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannel(channelMap);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void createChannelGroup(ReadableMap channelGroupMap, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannelGroup(channelGroupMap);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void createChannelGroups(ReadableArray channelGroupsArray, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannelGroups(channelGroupsArray);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void createChannels(ReadableArray channelsArray, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannels(channelsArray);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void deleteChannelGroup(String channelId, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.deleteChannelGroup(channelId);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void deleteChannel(String channelId, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.deleteChannel(channelId);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannel(String channelId, Promise promise) {
    Tasks.call(() -> NotifeeNotificationChannel.getChannel(channelId)).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannels(Promise promise) {
    Tasks.call(NotifeeNotificationChannel::getChannels).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannelGroup(String channelGroupId, Promise promise) {
    Tasks.call(() -> NotifeeNotificationChannel.getChannelGroup(channelGroupId)).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannelGroups(Promise promise) {
    Tasks.call(NotifeeNotificationChannel::getChannelGroups).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }
}
