with recursive asset_children as (
    select     *
    from       asset_entity
    where      parent_id in (<parentIds>)
    union all
    select     c.*
    from       asset_entity c inner join asset_children on c.parent_id = asset_children.id
)
select * from asset_children
where documents is not null