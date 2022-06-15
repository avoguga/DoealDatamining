import React, { useLayoutEffect } from 'react';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';

export const Logout = () => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    dispatch(logout());
    if (logoutUrl) {
      window.location.href = logoutUrl;
    }
  });

  return (
    <div className="p-5">
      <h4>Compartilhe o Facilita com amigos!</h4>
    </div>
  );
};

export default Logout;
