def content(a,b=None,c=None):
    print("a=",a,"b=",b,"c=",c)

def handler(**kargs):
    if "b" not in kargs:
        kargs["b"] = "a"
    return kargs

def contentA(a,**kargs):
    content(a,**handler(**kargs))

def contentB():
    contentA(a="a",b="b",c="c")


if __name__ == "__main__":
    contentB()