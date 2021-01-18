package in.niya.note.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Locale;

import in.niya.note.App;
import in.niya.note.R;
import in.niya.note.db.Controller;
import in.niya.note.db.OpenHelper;

public class Category extends DatabaseModel {
	public static final int THEME_RED       = 0;
	public static final int THEME_PINK      = 1;
	public static final int THEME_PURPLE    = 2;
	public static final int THEME_BLUE      = 3;
	public static final int THEME_CYAN      = 4;
	public static final int THEME_TEAL      = 5;
	public static final int THEME_GREEN     = 6;
	public static final int THEME_AMBER     = 7;
	public static final int THEME_ORANGE    = 8;

	public int counter;

	public Category() {}


	public Category(Cursor c) {
		super(c);
		this.theme = c.getInt(c.getColumnIndex(OpenHelper.COLUMN_THEME));
		this.counter = c.getInt(c.getColumnIndex(OpenHelper.COLUMN_COUNTER));
	}


	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();

		if (id == DatabaseModel.NEW_MODEL_ID) {
			values.put(OpenHelper.COLUMN_TYPE, type);
			values.put(OpenHelper.COLUMN_DATE, createdAt);
			values.put(OpenHelper.COLUMN_COUNTER, counter);
			values.put(OpenHelper.COLUMN_ARCHIVED, isArchived);
		}

		values.put(OpenHelper.COLUMN_TITLE, title);
		values.put(OpenHelper.COLUMN_THEME, theme);

		return values;
	}

	public static int getStyle(int theme) {
		switch (theme) {
			case THEME_RED:
				return R.style.AppThemeRed;
			case THEME_PINK:
				return R.style.AppThemePink;
			case THEME_AMBER:
				return R.style.AppThemeAmber;
			case THEME_BLUE:
				return R.style.AppThemeBlue;
			case THEME_CYAN:
				return R.style.AppThemeCyan;
			case THEME_GREEN:
				return R.style.AppThemeGreen;
			case THEME_ORANGE:
				return R.style.AppThemeOrange;
			case THEME_PURPLE:
				return R.style.AppThemePurple;
			case THEME_TEAL:
				return R.style.AppThemeTeal;
		}

		return R.style.AppTheme;
	}

	public static Category find(long id) {
		return Controller.instance.findNote(Category.class, id);
	}

	public static ArrayList<Category> all() {
		return Controller.instance.findNotes(
				Category.class,
				new String[] {
						OpenHelper.COLUMN_ID,
						OpenHelper.COLUMN_TITLE,
						OpenHelper.COLUMN_DATE,
						OpenHelper.COLUMN_TYPE,
						OpenHelper.COLUMN_ARCHIVED,
						OpenHelper.COLUMN_THEME,
						OpenHelper.COLUMN_COUNTER
				},
				OpenHelper.COLUMN_TYPE + " = ? AND " + OpenHelper.COLUMN_ARCHIVED + " = ?",
				new String[]{
						String.format(Locale.US, "%d", DatabaseModel.TYPE_CATEGORY),
						"0"
				},
				App.sortCategoriesBy
		);
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Category && id == (((Category) o).id);
	}
}
