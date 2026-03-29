db = db.getSiblingDB('asset_tracker');

db.createUser({
  user: 'asset_user',
  pwd: 'asset_pass',
  roles: [{ role: 'readWrite', db: 'asset_tracker' }]
});