package com.theladders.solid.lsp;

import java.util.Map.Entry;
import java.util.Set;


public abstract class Environment
{
  public static final String KEY_EMAIL_DOMAIN = "emaildomain";

  /**
   * Convenience method that returns the admin email address for this ladder.
   *
   * @return email address or "" if either the user or domain is not defined
   */
  public String getAdminEmail()
  {
    String user = getString("admin");
    String domain = getString(KEY_EMAIL_DOMAIN);

    return user.length() > 0 && domain.length() > 0 ? user + "@" + domain : "";
  }

  public String getString(String key)
  {
    Object val = get(key);
    return (val != null) ? val.toString().trim() : "";
  }

  public abstract Object get(Object key);

  public abstract Set<Entry<Object, Object>> entrySet();

}
