
-- simple join
select * from <baseModel> <?alias>
join <joinModel> <?alias> on <baseModel.id> = <joinModel.baseModel_id>



/* 
First use case: 
    base model and one join

 */

-- Select Phase
select * from base b

-- Join Phase
join mando m on b.id = m.base_id
