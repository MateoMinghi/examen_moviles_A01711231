https://api.api-ninjas.com/v1/covid19

Returns either a time series of Covid-19 case counts/deaths for a country/region or a single-day snapshot of every country in the world (see parameters).

Parameters
*Either date or country must be set.*

date  optional
Date to retrieve single-day snapshot. Must be in the form of YYYY-MM-DD (e.g. 2022-01-01).

country  optional
Country name (case insensitive).

region  optional
Administrative region (also known as state or province in many countries) name (case insensitive). Must be used in conjunction with country. If not set, countries with data broken down by administrative regions will return separate data for each region.

county  optional
County name for US states (case insensitive). For United States data only. Must be used in conjunction with country (United States) and region (e.g. California).

type  optional
Type of data to retrieve. Must be either cases or deaths. If not set, cases will be used by default.

Headers
X-Api-Key  required
API Key associated with your account.




Example request:

GET https://api.api-ninjas.com/v1/covid19?date=2022-01-01
X-Api-Key = key


[
    {
        "country": "Afghanistan",
        "region": "",
        "cases": {
            "total": 158107,
            "new": 23
        }
    },
    {
        "country": "Albania",
        "region": "",
        "cases": {
            "total": 210224,
            "new": 0
        }
    },
    {
        "country": "Algeria",
        "region": "",
        "cases": {
            "total": 218818,
            "new": 386
        }
    },
    ...]

GET https://api.api-ninjas.com/v1/covid19?country=canada
X-Api-Key = key

[
    {
        "country": "Canada",
        "region": "Alberta",
        "cases": {
            "2020-01-22": {
                "total": 0,
                "new": 0
            },
            "2020-01-23": {
                "total": 0,
                "new": 0
            },
            "2020-01-24": {
                "total": 0,
                "new": 0
            },
            "2020-01-25": {
                "total": 0,
                "new": 0
            },
            "2020-01-26": {
                "total": 0,
                "new": 0
            },
            ...]
