import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Concessao from './concessao';
import ConcessaoDetail from './concessao-detail';
import ConcessaoUpdate from './concessao-update';
import ConcessaoDeleteDialog from './concessao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConcessaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConcessaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConcessaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Concessao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConcessaoDeleteDialog} />
  </>
);

export default Routes;
