import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Diario from './diario';
import DiarioDetail from './diario-detail';
import DiarioUpdate from './diario-update';
import DiarioDeleteDialog from './diario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DiarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DiarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DiarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Diario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DiarioDeleteDialog} />
  </>
);

export default Routes;
