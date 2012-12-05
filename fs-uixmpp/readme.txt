


>.sample config of tigase server:

file:<tigase-install-home>/etc/init.properties
--cluster-mode = false
config-type = --gen-config-all
--cluster-nodes = fstest.com
--debug = server
--user-db = derby
--admins = admin@fstest.com
--user-db-uri = jdbc:derby:/home/wu/soft/tigase-5.1.0-beta8-b2937/tigasedb
--virt-hosts = fstest.com
--comp-class-1 = tigase.muc.MUCComponent
--comp-name-1 = muc
--sm-plugins = +jabber:iq:auth,+urn:ietf:params:xml:ns:xmpp-sasl,+urn:ietf:params:xml:ns:xmpp-bind,+urn:ietf:params:xml:ns:xmpp-session,+jabber:iq:register,+jabber:iq:roster,+presence,+jabber:iq:privacy,+jabber:iq:version,+http://jabber.org/protocol/stats,+starttls,+msgoffline,+vcard-temp,+http://jabber.org/protocol/commands,+jabber:iq:private,+urn:xmpp:ping,+basic-filter,+domain-filter,+pep,-zlib
bosh/max-inactivity[L]=10
>