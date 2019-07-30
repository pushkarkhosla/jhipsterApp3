import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBookEntity } from 'app/shared/model/book-entity.model';
import { AccountService } from 'app/core';
import { BookEntityService } from './book-entity.service';

@Component({
  selector: 'jhi-book-entity',
  templateUrl: './book-entity.component.html'
})
export class BookEntityComponent implements OnInit, OnDestroy {
  bookEntities: IBookEntity[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected bookEntityService: BookEntityService,
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
      this.bookEntityService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IBookEntity[]>) => res.ok),
          map((res: HttpResponse<IBookEntity[]>) => res.body)
        )
        .subscribe((res: IBookEntity[]) => (this.bookEntities = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.bookEntityService
      .query()
      .pipe(
        filter((res: HttpResponse<IBookEntity[]>) => res.ok),
        map((res: HttpResponse<IBookEntity[]>) => res.body)
      )
      .subscribe(
        (res: IBookEntity[]) => {
          this.bookEntities = res;
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
    this.registerChangeInBookEntities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBookEntity) {
    return item.id;
  }

  registerChangeInBookEntities() {
    this.eventSubscriber = this.eventManager.subscribe('bookEntityListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
