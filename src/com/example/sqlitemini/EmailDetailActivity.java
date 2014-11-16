package com.example.sqlitemini;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EmailDetailActivity extends Activity {
	private EditText mSubject;
	private EditText mSender;
	private EditText mPreview;


	private Uri emailUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.email_edit);

		mSubject = (EditText) findViewById(R.id.email_edit_subject);
		mSender = (EditText) findViewById(R.id.email_edit_sender);
		mPreview = (EditText) findViewById(R.id.email_edit_preview);
		Button confirmButton = (Button) findViewById(R.id.email_edit_button);

		Bundle extras = getIntent().getExtras();

		// check from the saved Instance
		emailUri = (bundle == null) ? null : (Uri) bundle
				.getParcelable(EmailContentProvider.CONTENT_ITEM_TYPE);

		// Or passed from the other activity
		if (extras != null) {
			emailUri = extras
					.getParcelable(EmailContentProvider.CONTENT_ITEM_TYPE);

			fillData(emailUri);
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(mSubject.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
	}

	private void fillData(Uri uri) {
		String[] projection = { EmailTable.COLUMN_SUBJECT,
				EmailTable.COLUMN_SENDER, EmailTable.COLUMN_PREVIEW,
				EmailTable.COLUMN_TIME };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			cursor.moveToFirst();
			
			mSubject.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(EmailTable.COLUMN_SENDER)));
			mSender.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(EmailTable.COLUMN_SENDER)));
			mPreview.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(EmailTable.COLUMN_PREVIEW)));

			// always close the cursor
			cursor.close();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(EmailContentProvider.CONTENT_ITEM_TYPE, emailUri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	private void saveState() {
		String subject =  mSubject.getText().toString();
		String sender = mSender.getText().toString();
		String preview = mPreview.getText().toString();

		// only save if either summary or description
		// is available

		if (subject.length() == 0 && sender.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(EmailTable.COLUMN_SUBJECT, subject);
		values.put(EmailTable.COLUMN_SENDER, sender);
		values.put(EmailTable.COLUMN_PREVIEW, preview);
		
		Time now = new Time();
		now.setToNow();
		String time = now.toString();
		
		
		values.put(EmailTable.COLUMN_TIME, time);


		if (emailUri == null) {
			// New email
			emailUri = getContentResolver().insert(
					EmailContentProvider.CONTENT_URI, values);
		} else {
			// Update email
			getContentResolver().update(emailUri, values, null, null);
		}
	}

	private void makeToast() {
		Toast.makeText(EmailDetailActivity.this, "Please maintain a summary",
				Toast.LENGTH_LONG).show();
	}
}
