import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomepageComponent } from './pages/admin-homepage/admin-homepage.component';
import { ViewRegistrationRequestsComponent } from './pages/admin-homepage/view-registration-requests/view-registration-requests.component';
import { ViewReportRequestComponent } from './pages/admin-homepage/view-report-request/view-report-request.component';
import { ViewVerificationReqComponent } from './pages/admin-homepage/view-verification-req/view-verification-req.component';
import { AgentHomepageComponent } from './pages/agent-homepage/agent-homepage.component';
import { NewCampaignComponent } from './pages/agent-homepage/new-campaign/new-campaign.component';
import { ViewCampaignComponent } from './pages/agent-homepage/view-campaign/view-campaign.component';
import { AgentRegistrationComponent } from './pages/agent-registration/agent-registration.component';
import { CloseFriendsComponent } from './pages/homepage/close-friends/close-friends.component';
import { FeedComponent } from './pages/homepage/feed/feed.component';
import { FollowRequestsComponent } from './pages/homepage/follow-requests/follow-requests.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { MyProfileComponent } from './pages/homepage/my-profile/my-profile.component';
import { NewAlbumComponent } from './pages/homepage/new-album/new-album.component';
import { NewPostComponent } from './pages/homepage/new-post/new-post.component';
import { NewStoryComponent } from './pages/homepage/new-story/new-story.component';
import { NewVerificationRequestComponent } from './pages/homepage/new-verification-request/new-verification-request.component';
import { NotificationSettingsComponent } from './pages/homepage/notification-settings/notification-settings.component';
import { ProfileComponent } from './pages/homepage/profile/profile.component';
import { SearchComponent } from './pages/homepage/search/search.component';
import { ViewFavouritesComponent } from './pages/homepage/view-favourites/view-favourites.component';
import { ViewLikedComponent } from './pages/homepage/view-liked/view-liked.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ViewProfileComponent } from './pages/view-profile/view-profile.component';

const routes: Routes = [
  { path: '', pathMatch:'full', redirectTo:'login'},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'registrationAgent', component: AgentRegistrationComponent},
  { path: 'homepage', component: HomepageComponent, children:[
    { path: 'profile',component:ProfileComponent},
    { path: 'search',component:SearchComponent},
    { path: 'feed',component:FeedComponent},
    { path: 'new-story',component:NewStoryComponent},
    { path: 'new-post',component:NewPostComponent},
    { path: 'new-album',component:NewAlbumComponent},
    { path: 'new-verification-request',component:NewVerificationRequestComponent},
    { path: 'follow-requests',component:FollowRequestsComponent},
    { path: 'view-liked',component:ViewLikedComponent},
    { path: 'view-favourites',component:ViewFavouritesComponent},
    { path: 'close-friends',component:CloseFriendsComponent},
    { path: 'notification-settings',component:NotificationSettingsComponent}
  ]}, 
  { path: 'agent', component: AgentHomepageComponent, children:[
    { path: 'profile',component:ProfileComponent},
    { path: 'search',component:SearchComponent},
    { path: 'feed',component:FeedComponent},
    { path: 'new-story',component:NewStoryComponent},
    { path: 'new-post',component:NewPostComponent},
    { path: 'new-album',component:NewAlbumComponent},
    { path: 'new-verification-request',component:NewVerificationRequestComponent},
    { path: 'follow-requests',component:FollowRequestsComponent},
    { path: 'view-liked',component:ViewLikedComponent},
    { path: 'view-favourites',component:ViewFavouritesComponent},
    { path: 'close-friends',component:CloseFriendsComponent},
    { path: 'notification-settings',component:NotificationSettingsComponent},
    { path: 'new-campaign',component:NewCampaignComponent},
    { path: 'view-campaign',component:ViewCampaignComponent}
  ]}, 
  { path: 'admin', component:AdminHomepageComponent, children:[
    { path: 'viewVerifReq', component:ViewVerificationReqComponent},
    { path: 'view-report-request', component:ViewReportRequestComponent},
    { path: 'register-agent', component: AgentRegistrationComponent},
    { path: 'view-registration-requests', component: ViewRegistrationRequestsComponent},
  ]},
  { path: 'view-profile/:username', component: ViewProfileComponent},
  { path: 'my-profile', component: MyProfileComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
