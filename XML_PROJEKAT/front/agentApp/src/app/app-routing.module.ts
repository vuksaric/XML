import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgentHomepageComponent } from './pages/agent-homepage/agent-homepage.component';
import { ViewProductsComponent } from './pages/agent-homepage/view-products/view-products.component';
import { CreateProductComponent } from './pages/agent-homepage/create-product/create-product.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ViewProductComponent } from './pages/view-product/view-product.component';

const routes: Routes = [
  { path: '', pathMatch:'full', redirectTo:'login'},
  { path: 'registration', component: RegistrationComponent},
  { path: 'login', component: LoginComponent},
  { path: 'agent', component: AgentHomepageComponent, children:[
    { path: 'createProduct', component: CreateProductComponent},
    { path: 'viewProducts', component: ViewProductsComponent}
  ]},
  { path: 'viewProduct/:id', component: ViewProductComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
