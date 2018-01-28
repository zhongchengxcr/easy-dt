db.transaction_message.ensureIndex({messageId:1},{unique:true})
db.transaction_message.ensureIndex({domain:1})