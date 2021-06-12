import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { LoginComponent } from './pages/login/login.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DemoNgZorroAntdModule } from './ng-zorro-antd.module';
import { ToastrModule } from 'ngx-toastr';
import { NzTableModule } from 'ng-zorro-antd/table';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { ProfileComponent } from './pages/homepage/profile/profile.component';
import { SearchComponent } from './pages/homepage/search/search.component';
import { FeedComponent } from './pages/homepage/feed/feed.component';
import { SearchPipe } from './pages/homepage/search/search.pipe';
import { NewPostComponent } from './pages/homepage/new-post/new-post.component';
import { NewStoryComponent } from './pages/homepage/new-story/new-story.component';
import { NewVerificationRequestComponent } from './pages/homepage/new-verification-request/new-verification-request.component';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    HomepageComponent,
    ProfileComponent,
    SearchComponent,
    FeedComponent,
    SearchPipe,
    NewPostComponent,
    NewStoryComponent,
    NewVerificationRequestComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    DemoNgZorroAntdModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NzTableModule,
    ToastrModule.forRoot()
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }],
  bootstrap: [AppComponent]
})
export class AppModule { }
