from bs4 import BeautifulSoup
import re, json


def read_html_content(content: str):
    soup = BeautifulSoup(content, "html.parser")
    pattern = re.compile(r"var ip_begin = (.*?);", re.MULTILINE | re.DOTALL)
    ip_begin = soup.find("script", text=pattern)
    return pattern.search(ip_begin.text).group(1)
