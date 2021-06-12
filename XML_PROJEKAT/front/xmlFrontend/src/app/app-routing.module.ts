import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomepageComponent } from './pages/admin-homepage/admin-homepage.component';
import { ViewVerificationReqComponent } from './pages/admin-homepage/view-verification-req/view-verification-req.component';
import { FeedComponent } from './pages/homepage/feed/feed.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { NewPostComponent } from './pages/homepage/new-post/new-post.component';
import { NewStoryComponent } from './pages/homepage/new-story/new-story.component';
import { NewVerificationRequestComponent } from './pages/homepage/new-verification-request/new-verification-request.component';
import { ProfileComponent } from './pages/homepage/profile/profile.component';
import { SearchComponent } from './pages/homepage/search/search.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';

const routes: Routes = [
  { path: '', pathMatch:'full', redirectTo:'login'},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'homepage', component: HomepageComponent, children:[
    { path: 'profile',component:ProfileComponent},
    { path: 'search',component:SearchComponent},
    { path: 'feed',component:FeedComponent},
    { path: 'new-story',component:NewStoryComponent},
    { path: 'new-post',component:NewPostComponent},
    { path: 'new-verification-request',component:NewVerificationRequestComponent}
  ]}, 
  { path: 'admin', component:AdminHomepageComponent, children:[
    { path: 'viewVerifReq', component:ViewVerificationReqComponent}
  ]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
