import { ActivatedRoute } from '@angular/router';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NotificationService } from 'src/app/core/services/NotificationService';


@Component({
selector: 'app-index',
templateUrl: './index.component.html',
styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {

    constructor() {
    }
    
    ngOnInit(): void {
        
    }
}
