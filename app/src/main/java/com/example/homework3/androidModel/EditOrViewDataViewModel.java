package com.example.homework3.androidModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.homework3.javaModel.CardSetDataModel;
import com.example.homework3.javaModel.NoteCardDataModel;

public class EditOrViewDataViewModel extends AndroidViewModel {
    public static final int NOT_APPLICABLE = -1;
    public static final int CREATE_NEW_OBJECT = 0;
    public static final int EDIT_OBJECT = 1;

    private CardSetDataModel cardset;
    private NoteCardDataModel notecard;
    private int status_code;

    public EditOrViewDataViewModel(@NonNull Application application) {
        super(application);
    }

    public CardSetDataModel getCardset() {
        return cardset;
    }

    public void setCardset(CardSetDataModel cardset) {
        this.cardset = cardset;
    }

    public NoteCardDataModel getNotecard() {
        return notecard;
    }

    public void setNotecard(NoteCardDataModel notecard) {
        this.notecard = notecard;
    }

    public int getStatusCode() {
        return status_code;
    }

    public void setStatusCode(int status_code) {
        this.status_code = status_code;
    }
}
