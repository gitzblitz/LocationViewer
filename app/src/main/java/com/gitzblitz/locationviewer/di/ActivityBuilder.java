package com.gitzblitz.locationviewer.di;

import com.gitzblitz.locationviewer.ui.AddEditLocationActivity;
import com.gitzblitz.locationviewer.ui.DetailLocationActivity;
import com.gitzblitz.locationviewer.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainListActivity();

    @ContributesAndroidInjector
    abstract AddEditLocationActivity bindAddEditLocationActivity();

    @ContributesAndroidInjector
    abstract DetailLocationActivity bindDetailsLocationActivity();

}
