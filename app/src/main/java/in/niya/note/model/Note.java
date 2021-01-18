package in.niya.note.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Locale;

import in.niya.note.App;
import in.niya.note.db.Controller;
import in.niya.note.db.OpenHelper;

public class Note extends DatabaseModel {
	public long categoryId;
	public String body;

	public Note() {}

	public Note(Cursor c) {
		super(c);
		this.categoryId = c.getLong(c.getColumnIndex(OpenHelper.COLUMN_PARENT_ID));
		try {
			this.body = c.getString(c.getColumnIndex(OpenHelper.COLUMN_BODY));
		} catch (Exception ignored) {}
	}

	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();

		if (id == DatabaseModel.NEW_MODEL_ID) {
			values.put(OpenHelper.COLUMN_TYPE, type);
			values.put(OpenHelper.COLUMN_DATE, createdAt);
			values.put(OpenHelper.COLUMN_ARCHIVED, isArchived);
			values.put(OpenHelper.COLUMN_PARENT_ID, categoryId);
		}

		values.put(OpenHelper.COLUMN_TITLE, title);
		values.put(OpenHelper.COLUMN_BODY, body);

		return values;
	}

	public static Note find(long id) {
		return Controller.instance.findNote(Note.class, id);
	}

	public static ArrayList<Note> all(long categoryId) {
		return Controller.instance.findNotes(
				Note.class,
				new String[] {
						OpenHelper.COLUMN_ID,
						OpenHelper.COLUMN_TITLE,
						OpenHelper.COLUMN_DATE,
						OpenHelper.COLUMN_TYPE,
						OpenHelper.COLUMN_ARCHIVED,
						OpenHelper.COLUMN_PARENT_ID,
				},
				OpenHelper.COLUMN_TYPE + " != ? AND " + OpenHelper.COLUMN_PARENT_ID + " = ? AND " + OpenHelper.COLUMN_ARCHIVED + " = ?",
				new String[]{
						String.format(Locale.US, "%d", DatabaseModel.TYPE_CATEGORY),
						String.format(Locale.US, "%d", categoryId),
						"0"
				},
				App.sortNotesBy
		);
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Note && id == (((Note) o).id);
	}
}
