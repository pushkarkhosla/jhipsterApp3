import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPushkarJhipster } from 'app/shared/model/pushkar-jhipster.model';
import { AccountService } from 'app/core';
import { PushkarJhipsterService } from './pushkar-jhipster.service';

@Component({
  selector: 'jhi-pushkar-jhipster',
  templateUrl: './pushkar-jhipster.component.html'
})
export class PushkarJhipsterComponent implements OnInit, OnDestroy {
  pushkarJhipsters: IPushkarJhipster[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected pushkarJhipsterService: PushkarJhipsterService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.pushkarJhipsterService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPushkarJhipster[]>) => res.ok),
          map((res: HttpResponse<IPushkarJhipster[]>) => res.body)
        )
        .subscribe((res: IPushkarJhipster[]) => (this.pushkarJhipsters = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.pushkarJhipsterService
      .query()
      .pipe(
        filter((res: HttpResponse<IPushkarJhipster[]>) => res.ok),
        map((res: HttpResponse<IPushkarJhipster[]>) => res.body)
      )
      .subscribe(
        (res: IPushkarJhipster[]) => {
          this.pushkarJhipsters = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPushkarJhipsters();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPushkarJhipster) {
    return item.id;
  }

  registerChangeInPushkarJhipsters() {
    this.eventSubscriber = this.eventManager.subscribe('pushkarJhipsterListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
