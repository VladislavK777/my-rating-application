select
    ju.id,
    ju.login,
    up.partner_name,
    up.url,
    up.fee,
    up.requisites_data,
    payment_details."year",
    payment_details."month",
    payment_details."count",
    payment_details."count" * up.fee as "payment",
    row_number () over (
		partition by ju.id
		order by payment_details."year", payment_details."month") as "order_by"
from
    jhi_user ju,
    user_profile up,
    (
        select
            date_part('month', or2.created_date ::date) as "month",
            date_part('year', or2.created_date ::date) as "year",
            count(*) as "count",
            or2.partner_user_id
        from
            order_request or2
            where or2.status not in ('NEW')
        group by
            1,
            2,
            or2.partner_user_id) payment_details
where
        ju.id = payment_details.partner_user_id
  and ju.user_profile_id = up.id
