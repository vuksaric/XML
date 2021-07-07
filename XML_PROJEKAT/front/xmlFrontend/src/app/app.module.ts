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
import { AdminHomepageComponent } from './pages/admin-homepage/admin-homepage.component';
import { ViewVerificationReqComponent } from './pages/admin-homepage/view-verification-req/view-verification-req.component';
import { ViewProfileComponent } from './pages/view-profile/view-profile.component';
import { FollowRequestsComponent } from './pages/homepage/follow-requests/follow-requests.component';
import { ViewLikedComponent } from './pages/homepage/view-liked/view-liked.component';
import { NewAlbumComponent } from './pages/homepage/new-album/new-album.component';
import { SearchFilterPipe } from './pipes/search-filter.pipe';
import { ViewFavouritesComponent } from './pages/homepage/view-favourites/view-favourites.component';
import { CloseFriendsComponent } from './pages/homepage/close-friends/close-friends.component';
import { NotificationSettingsComponent } from './pages/homepage/notification-settings/notification-settings.component';
import { ViewReportRequestComponent } from './pages/admin-homepage/view-report-request/view-report-request.component';

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
    AdminHomepageComponent,
    ViewVerificationReqComponent,
    ViewProfileComponent,
    FollowRequestsComponent,
    ViewLikedComponent,
    NewAlbumComponent,
    SearchFilterPipe,
    ViewFavouritesComponent,
    CloseFriendsComponent,
    NotificationSettingsComponent,
    ViewReportRequestComponent
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
    ToastrModule.forRoot(),
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }],
  bootstrap: [AppComponent]
})
export class AppModule { }
