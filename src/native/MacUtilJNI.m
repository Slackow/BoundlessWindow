#import <Cocoa/Cocoa.h>
#import <jni.h>
#import "com_slackow_boundlesswindow_MacPresentationUtil.h"


/*  void nativeSetPresentation(long options)  */
JNIEXPORT void JNICALL
Java_com_slackow_boundlesswindow_MacPresentationUtil_nativeSetPresentation
        (JNIEnv *env, jclass cls, jlong opts)
{
    // Cocoa calls must run on main thread
    NSApplication *app = [NSApplication sharedApplication];
    [app setPresentationOptions:(NSApplicationPresentationOptions) opts];
}

/*  int[] visibleFramePx()  (top-left origin, unscaled points) */
JNIEXPORT jintArray JNICALL
Java_com_slackow_boundlesswindow_MacPresentationUtil_nativeVisibleFrame
        (JNIEnv *env, jclass cls)
{
    NSScreen *scr = [NSScreen mainScreen];
    if (!scr) return NULL;

    NSRect vis   = [scr visibleFrame];
    NSRect frame = [scr frame];

    // Convert origin.y from bottom-left to top-left
    CGFloat topLeftY = frame.origin.y + frame.size.height
                     - vis.origin.y - vis.size.height;

    jint out[4] = {
        (jint) llround(vis.origin.x),
        (jint) llround(topLeftY),
        (jint) llround(vis.size.width),
        (jint) llround(vis.size.height)
    };

    jintArray arr = (*env)->NewIntArray(env, 4);
    (*env)->SetIntArrayRegion(env, arr, 0, 4, out);
    return arr;
}