import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PDF from './pdf';
import PDFDetail from './pdf-detail';
import PDFUpdate from './pdf-update';
import PDFDeleteDialog from './pdf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PDFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PDFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PDFDetail} />
      <ErrorBoundaryRoute path={match.url} component={PDF} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PDFDeleteDialog} />
  </>
);

export default Routes;
