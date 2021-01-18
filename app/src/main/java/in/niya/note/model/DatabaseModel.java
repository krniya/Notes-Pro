package in.niya.note.model;

import android.content.ContentValues;
import android.database.Cursor;
import in.niya.note.R;
import in.niya.note.db.Controller;
import in.niya.note.db.OpenHelper;

abstract public class DatabaseModel {
	public static final int TYPE_CATEGORY = 0;
	public static final int TYPE_NOTE_SIMPLE = 1;
	public static final int TYPE_NOTE_DRAWING = 2;

	public static final long NEW_MODEL_ID = -1;

	public long id = NEW_MODEL_ID;
	public int type;
	public String title;
	public long createdAt;
	public boolean isArchived;
	public int theme;

	public int position = 0;

	public DatabaseModel() {}

	public DatabaseModel(Cursor c) {
		this.id = c.getLong(c.getColumnIndex(OpenHelper.COLUMN_ID));
		this.type = c.getInt(c.getColumnIndex(OpenHelper.COLUMN_TYPE));
		this.title = c.getString(c.getColumnIndex(OpenHelper.COLUMN_TITLE));
		try {
			this.createdAt = Long.parseLong(c.getString(c.getColumnIndex(OpenHelper.COLUMN_DATE)));
		} catch (NumberFormatException nfe) {
			this.createdAt = System.currentTimeMillis();
		}
		this.isArchived = c.getInt(c.getColumnIndex(OpenHelper.COLUMN_ARCHIVED)) == 1;
	}

	public long save() {
		return Controller.instance.saveNote(this, getContentValues());
	}


	public boolean toggle() {
		ContentValues values = new ContentValues();
		values.put(OpenHelper.COLUMN_ARCHIVED, !isArchived);

		if (Controller.instance.saveNote(this, values) != DatabaseModel.NEW_MODEL_ID) {
			isArchived = !isArchived;
			return true;
		}

		return false;
	}

	public int getThemeBackground() {
		switch (theme) {
			case Category.THEME_RED:
				return R.drawable.circle_red;
			case Category.THEME_PINK:
				return R.drawable.circle_pink;
			case Category.THEME_AMBER:
				return R.drawable.circle_amber;
			case Category.THEME_BLUE:
				return R.drawable.circle_blue;
			case Category.THEME_CYAN:
				return R.drawable.circle_cyan;
			case Category.THEME_GREEN:
				return R.drawable.circle_green;
			case Category.THEME_ORANGE:
				return R.drawable.circle_orange;
			case Category.THEME_PURPLE:
				return R.drawable.circle_purple;
			case Category.THEME_TEAL:
				return R.drawable.circle_teal;
		}

		return R.drawable.circle_main;
	}

	abstract public ContentValues getContentValues();

	@Override
	public int hashCode() {
		return (int) id;
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof DatabaseModel && id == (((DatabaseModel) o).id);
	}
}
