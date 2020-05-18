package com.example.w_mvvm_databinding.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
}
