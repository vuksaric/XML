import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agent-homepage',
  templateUrl: './agent-homepage.component.html',
  styleUrls: ['./agent-homepage.component.css']
})
export class AgentHomepageComponent implements OnInit {

  isCollapsed = false;
  userID: number = 0;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  logout(){
    //ovde obrisati sve sto se tice tokena
    //localStorage.clear();
    this.router.navigate(['login']);
  }

  CreateProduct(){
    this.router.navigate(['agent/createProduct']);
  }
  
  ViewProducts(){
    this.router.navigate(['agent/viewProducts']);
  }
  


}
