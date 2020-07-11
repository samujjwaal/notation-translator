package com.samashish.notetrans;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;


public class CustomSelectionActionModeCallback implements ActionMode.Callback {
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        try {
            menu.removeItem(android.R.id.paste);
            menu.removeItem(android.R.id.cut);
        } catch (Exception e) {
            // ignored
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }
}
