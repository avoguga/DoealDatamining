import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Servidor from './servidor';
import ServidorDetail from './servidor-detail';
import ServidorUpdate from './servidor-update';
import ServidorDeleteDialog from './servidor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServidorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServidorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServidorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Servidor} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServidorDeleteDialog} />
  </>
);

export default Routes;
