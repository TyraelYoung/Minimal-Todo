package com.csmall.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;

import com.csmall.log.LogHelper;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

/**
 * Created by wangchao on 2017/4/10.
 */

public class CopyPasteHelper {
    private static final String TAG = "CopyPasteHelper";

    public static void copyText(String content) {
        Context context = ApplicationHolder.getApplication();
        // Gets a handle to the clipboard service.
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text", content);
        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip);
    }

    public static boolean hasText() {
        Context context = ApplicationHolder.getApplication();
        // Gets a handle to the clipboard service.
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        // If the clipboard doesn't contain data, disable the paste menu item.
// If it does contain data, decide if you can handle the data.
        if (!(clipboard.hasPrimaryClip())) {

            return false;

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {

            // This disables the paste menu item, since the clipboard has data but it is not plain text
            return false;
        } else {

            // This enables the paste menu item, since the clipboard contains plain text.
            return true;
        }
    }

    public static String pasteText() {
        if(!hasText()){
            return null;
        }
        LogHelper.d(TAG, "pasteText");
        Context context = ApplicationHolder.getApplication();
        // Gets a handle to the clipboard service.
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
// Examines the item on the clipboard. If getText() does not return null, the clip item contains the
// text. Assumes that this application can only handle one item at a time.
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

// Gets the clipboard as text.
        String pasteData = item.getText().toString();

// If the string contains data, then the paste operation is done
        if (pasteData != null) {
            return pasteData;

// The clipboard does not contain text. If it contains a URI, attempts to get data from it
        } else {
            Uri pasteUri = item.getUri();

            // If the URI contains something, try to get text from it
            if (pasteUri != null) {

                // calls a routine to resolve the URI and get data from it. This routine is not
                // presented here.
//                pasteData = resolveUri(Uri);
                return pasteData;
            } else {

                // Something is wrong. The MIME type was plain text, but the clipboard does not contain either
                // text or a Uri. Report an error.
                LogHelper.e(TAG, "Clipboard contains an invalid data type");
                return null;
            }
        }
    }
}

