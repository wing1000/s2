# 基本的分片策略： #

1. user_pwd 不分片。
2. 除user_pwd之外其他和User/profile相关的表都以以user id来分片。
3. photos及其相关的表以photo_id来分片， photos的photo_id 通过Sequence表生成
4. relationship 模块以source_id分片。
